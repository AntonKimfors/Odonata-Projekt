package com.kwizgeeq.model;

import java.io.Serializable;

/**
 * Created by Henrik on 15/05/2017.
 *
 * * @author Henrik Håkansson
 * revised by Are Ehnberg, Marcus Olsson Lindvärn and Anton Kimfors
 */

public enum AnswerType implements Serializable {
    TEXT,IMAGE;



    @Override
    public String toString() {
        switch(this) {
            case TEXT: return "TEXT";
            case IMAGE: return "IMAGE";
            default: throw new IllegalArgumentException();
        }
    }
}
