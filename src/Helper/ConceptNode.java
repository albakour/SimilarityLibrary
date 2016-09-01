/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import ConceptRelatedness.Concept.Concept;

/**
 *
 * @author sobhy
 */
public class ConceptNode {
    // the value 
    Concept concept;
    ConceptNode child;
    ConceptNode parent;
    int depth;
    int level1;
    int level2;

    public ConceptNode(Concept concept, ConceptNode child, ConceptNode parent) {
        this.concept = concept;
        this.child = child;
        this.parent = parent;
        level1 = Integer.MAX_VALUE;
        level2 = Integer.MAX_VALUE;
    }

    public boolean equals(ConceptNode node) {
        if (node.concept.getValue().equals(this.concept.getValue())) {
            return true;
        }
        return false;

    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel1() {
        return this.level1;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public int getLevel2() {
        return this.level2;
    }

    public Concept getConcept() {
        return this.concept;
    }

    public ConceptNode getParent() {
        return this.parent;
    }

    public void setParent(ConceptNode parent) {
        this.parent = parent;
    }

    public ConceptNode getChild() {
        return this.child;
    }

    public void setChild(ConceptNode child) {
        this.child = child;
    }

}
