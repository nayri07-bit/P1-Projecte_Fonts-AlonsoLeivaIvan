/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package p1.t4.idao;
import p1.t4.model.User;

/**
 *
 * @author anton
 */
public interface IDAOUser {
    User trobarUser(String username) throws Exception;
    boolean comprobarUser(String username) throws Exception;
}
