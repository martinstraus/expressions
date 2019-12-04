/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

/**
 *
 * @author martinstraus
 */
public class ParameterDefinition {

    private final String name;

    public ParameterDefinition(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

}
