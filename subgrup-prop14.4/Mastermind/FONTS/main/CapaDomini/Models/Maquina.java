package main.CapaDomini.Models;

import java.util.List;

/**
 * Helpful to swap between algorithms.
 * Any class implementing Maquina interface should implement the solve method.
 */
public interface Maquina {
    public List<List<Integer>> solve(List<Integer> solution) throws Exception;
}
