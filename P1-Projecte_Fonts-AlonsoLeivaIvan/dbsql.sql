drop table prov_comp cascade constraints;
drop table prod_item cascade constraints;
drop table proveidor cascade constraints;
drop table producte cascade constraints;
drop table component cascade constraints;
drop table item cascade constraints;
drop table municipi cascade constraints;
drop table provincia cascade constraints;
drop table unitat_mesura cascade constraints;
drop table usuarios cascade constraints;

create or replace package pkg_prov_comp as
    g_trigger_active boolean := false;
end pkg_prov_comp;
/
create or replace package body pkg_prov_comp as
end pkg_prov_comp;
/

create table usuarios (
    id integer primary key,
    username varchar2(50) not null,
    password varchar2(255) not null
);

create table unitat_mesura (
    um_codi integer primary key,
    um_nom varchar2(50) not null
);

create table provincia (
    pr_codi integer primary key,
    pr_nom varchar2(50) not null
);

create table municipi (
    mu_pr_codi integer not null,
    mu_num integer not null,
    mu_nom varchar2(100) not null,
    constraint pk_municipi primary key (mu_pr_codi, mu_num),
    constraint fk_municipi_prov foreign key (mu_pr_codi) references provincia(pr_codi)
);

create table item (
    it_codi integer primary key,
    it_tipus char(1) check (it_tipus in ('c','p')),
    it_nom varchar2(100) not null,
    it_desc varchar2(200),
    it_stock integer not null,
    it_foto blob not null
);

create table component (
    cm_codi integer primary key,
    cm_um_codi integer not null,
    cm_codi_fabricant varchar2(50) not null,
    cm_preu_mig integer not null,
    constraint fk_component_item foreign key (cm_codi) references item(it_codi),
    constraint fk_component_um foreign key (cm_um_codi) references unitat_mesura(um_codi)
);

create table producte (
    pr_codi integer primary key,
    constraint fk_producte_item foreign key (pr_codi) references item(it_codi)
);

create table prod_item (
    pi_pr_codi integer not null,
    pi_it_codi integer not null,
    quantitat integer not null,
    constraint pk_prod_item primary key (pi_pr_codi, pi_it_codi),
    constraint fk_prod_item_prod foreign key (pi_pr_codi) references producte(pr_codi),
    constraint fk_prod_item_item foreign key (pi_it_codi) references item(it_codi),
    constraint chk_prod_item_quantitat check (quantitat > 0),
    constraint chk_prod_item_diff check (pi_pr_codi <> pi_it_codi)
);

create table proveidor (
    pv_codi integer primary key,
    pv_cif varchar2(15) unique not null,
    pv_rao_social varchar2(100) not null,
    pv_lin_adre_fac varchar2(150) not null,
    pv_persona_contacte varchar2(100),
    pv_telef_contacte varchar2(20),
    pv_mu_pr_codi integer not null,
    pv_mu_num integer not null,
    constraint fk_proveidor_mun foreign key (pv_mu_pr_codi, pv_mu_num) references municipi(mu_pr_codi, mu_num)
);

create table prov_comp (
    pc_cm_it_codi integer not null,
    pc_pv_codi integer not null,
    pc_preu integer not null,
    constraint pk_prov_comp primary key (pc_cm_it_codi, pc_pv_codi),
    constraint fk_prov_comp_comp foreign key (pc_cm_it_codi) references component(cm_codi) on delete cascade,
    constraint fk_prov_comp_pv foreign key (pc_pv_codi) references proveidor(pv_codi),
    constraint chk_prov_comp_preu check (pc_preu > 0)
);

create or replace function get_preu_mig(p_cm_it_codi in integer)
return number
is
    v_total number := 0;
    v_count number := 0;
    v_preu_mig number := 0;
begin
    for rec in (select pc_preu from prov_comp where pc_cm_it_codi = p_cm_it_codi) loop
        v_total := v_total + rec.pc_preu;
        v_count := v_count + 1;
    end loop;
    if v_count > 0 then
        v_preu_mig := v_total / v_count;
    else
        v_preu_mig := 0;
    end if;
    return v_preu_mig;
end;
/

create or replace trigger trg_component_bef
before insert or update on component
for each row
begin
    if inserting then
        :new.cm_preu_mig := 0;
    end if;
    if updating then
        if :old.cm_preu_mig <> :new.cm_preu_mig and not pkg_prov_comp.g_trigger_active then
            raise_application_error(-20001, 'no se puede modificar cm_preu_mig');
        end if;
        if :old.cm_codi <> :new.cm_codi then
            raise_application_error(-20002, 'no se puede modificar cm_codi');
        end if;
    end if;
end;
/

create or replace trigger trg_provcomp_bef
before update on prov_comp
for each row
begin
    if :old.pc_cm_it_codi <> :new.pc_cm_it_codi then
        raise_application_error(-20003, 'no se puede modificar pc_cm_it_codi');
    end if;
    if :old.pc_pv_codi <> :new.pc_pv_codi then
        raise_application_error(-20004, 'no se puede modificar pc_pv_codi');
    end if;
end;
/

create or replace trigger trg_provcomp_aft
after insert or update or delete on prov_comp
declare
    type t_cm_codi_tab is table of prov_comp.pc_cm_it_codi%type;
    v_cm_tab t_cm_codi_tab := t_cm_codi_tab();
begin
    pkg_prov_comp.g_trigger_active := true;

    select distinct pc_cm_it_codi bulk collect into v_cm_tab from prov_comp;

    for i in 1 .. v_cm_tab.count loop
        update component
        set cm_preu_mig = get_preu_mig(v_cm_tab(i))
        where cm_codi = v_cm_tab(i);
    end loop;

    pkg_prov_comp.g_trigger_active := false;
exception
    when others then
        pkg_prov_comp.g_trigger_active := false;
        raise;
end;
/

insert into provincia(pr_codi, pr_nom) values (1,'barcelona');
insert into provincia(pr_codi, pr_nom) values (2,'tarragona');

insert into municipi(mu_pr_codi, mu_num, mu_nom) values (1,101,'igualada');
insert into municipi(mu_pr_codi, mu_num, mu_nom) values (1,102,'manresa');
insert into municipi(mu_pr_codi, mu_num, mu_nom) values (1,103,'terrassa');
insert into municipi(mu_pr_codi, mu_num, mu_nom) values (2,201,'reus');
insert into municipi(mu_pr_codi, mu_num, mu_nom) values (2,202,'tarragona');

insert into unitat_mesura values (1,'unitats');
insert into unitat_mesura values (2,'kg');
insert into unitat_mesura values (3,'litres');
 
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1001,'c','motor v8','motor de combustió v8',50, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1002,'c','bateria 12v','bateria per automòbil',200, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1003,'c','roda 18"','roda d’aliatge 18 polzades',120, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1004,'c','porta esquerra','porta de cotxe esquerra',40, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1005,'c','porta dreta','porta de cotxe dreta',35, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1006,'c','seient davanter','seient de pell negre',60, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1007,'c','parabrises','vidre davanter',80, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1008,'c','fars led','kit fars led',100, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1009,'c','radiador','radiador refrigerant',55, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (1010,'c','alternador','alternador 90a',70, empty_blob());

insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (2001,'p','cotxe model a','cotxe complet model a',10, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (2002,'p','xassis model a','xassis del cotxe model a',15, empty_blob());
insert into item(it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) values (2003,'p','interior model a','interior del cotxe model a',20, empty_blob());


insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1001,1,'fab001',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1002,1,'fab002',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1003,1,'fab003',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1004,1,'fab004',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1005,1,'fab005',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1006,1,'fab006',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1007,1,'fab007',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1008,1,'fab008',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1009,1,'fab009',0);
insert into component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) values (1010,1,'fab010',0);


insert into producte(pr_codi) values (2001);
insert into producte(pr_codi) values (2002);
insert into producte(pr_codi) values (2003);


insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2001,2002,1);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2001,2003,1);

insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2002,1001,1);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2002,1002,1);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2002,1003,4);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2002,1004,1);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2002,1005,1);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2002,1009,1);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2002,1010,1);

insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2003,1006,4);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2003,1007,1);
insert into prod_item(pi_pr_codi, pi_it_codi, quantitat) values (2003,1008,2);

insert into proveidor(pv_codi, pv_cif, pv_rao_social, pv_lin_adre_fac, pv_persona_contacte, pv_telef_contacte, pv_mu_pr_codi, pv_mu_num) values (1001,'cif001','motors sa','carrer motor 1',null,null,1,101);
insert into proveidor(pv_codi, pv_cif, pv_rao_social, pv_lin_adre_fac, pv_persona_contacte, pv_telef_contacte, pv_mu_pr_codi, pv_mu_num) values (1002,'cif002','bateries sl','avda energia 10','laura torres','666111222',1,102);
insert into proveidor(pv_codi, pv_cif, pv_rao_social, pv_lin_adre_fac, pv_persona_contacte, pv_telef_contacte, pv_mu_pr_codi, pv_mu_num) values (1003,'cif003','rodes iberia','carrer rodes 25','pere vila','666222333',1,103);
insert into proveidor(pv_codi, pv_cif, pv_rao_social, pv_lin_adre_fac, pv_persona_contacte, pv_telef_contacte, pv_mu_pr_codi, pv_mu_num) values (1004,'cif004','vidrescat','carrer cristall 2',null,null,2,201);
insert into proveidor(pv_codi, pv_cif, pv_rao_social, pv_lin_adre_fac, pv_persona_contacte, pv_telef_contacte, pv_mu_pr_codi, pv_mu_num) values (1005,'cif005','il·luminacioauto','avda llum 3','marta soler','666333444',2,202);
insert into proveidor(pv_codi, pv_cif, pv_rao_social, pv_lin_adre_fac, pv_persona_contacte, pv_telef_contacte, pv_mu_pr_codi, pv_mu_num) values (1006,'cif006','recamvis manresa','carrer recamvis 7',null,null,1,102);
insert into proveidor(pv_codi, pv_cif, pv_rao_social, pv_lin_adre_fac, pv_persona_contacte, pv_telef_contacte, pv_mu_pr_codi, pv_mu_num) values (1007,'cif007','frenos tarraco','avda cotxe 8','joan puig','666444555',2,201);
insert into proveidor(pv_codi, pv_cif, pv_rao_social, pv_lin_adre_fac, pv_persona_contacte, pv_telef_contacte, pv_mu_pr_codi, pv_mu_num) values (1008,'cif008','parts universal','carrer global 99','sara díaz','666555666',1,103);


insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1001,1001,5000.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1001,1008,5200.00); 
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1002,1002,150.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1002,1003,1242.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1003,1003,200.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1004,1006,300.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1005,1006,310.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1006,1006,450.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1007,1004,250.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1008,1005,180.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1009,1007,600.00);
insert into prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) values (1010,1008,220.00);

commit;