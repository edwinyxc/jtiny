package com.shuimin.jtiny.core;

import static com.shuimin.base.S._notNull;

import com.shuimin.base.struc.tree.TinyTree;
import com.shuimin.base.struc.tree.Tree;

public class ResourceTree extends TinyTree<Resource> {

    public ResourceTree() {
    }

    public ResourceTree(Resource res) {
        super(res);
        elem(res);
    }

    @Override
    public ResourceTree elem(Resource t) {
        super.name(_notNull(t.name()));
        super.elem(t);
        return this;
    }

    @Override
    public ResourceTree select(String name) {
        if (name.equals("/")) {
            return (ResourceTree) this.root();
        }

        if (name.equals(".")) {
            return (ResourceTree) this;
        }

        if (name.equals("..")) {
            return (ResourceTree) this.parent();
        }

        for (Tree<Resource> res : children()) {
            if (res.name().equals(name)) {
                return (ResourceTree) res;
            }
        }
        return null;
    }

    @Override
    public ResourceTree select(String[] names) {
        ResourceTree cur = this;
        for (String name : names) {
            if (name.length() == 0) {
                continue;
            }
            cur = cur.select(name);
            if (cur == null) {
                break;
            }
        }
        return cur;
    }

    @Override
    public ResourceTree name(String name) {
        super.name(name);
        return this;
    }

}
