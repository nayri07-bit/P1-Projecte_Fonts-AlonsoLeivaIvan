/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package p1.t4.idao;

import java.util.List;
import p1.t4.model.Composicio;

/**
 *
 * @author anton
 */
public interface IDAOComposicio {
    List<Composicio> getFills(int itPare);
    void insert(int itPare, int itFill, int quantitat);
    void delete(int itPare, int itFill);
}
