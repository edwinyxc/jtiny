package com.shuimin.jtiny.core;

import static com.shuimin.base.S._notNull;

import com.shuimin.base.struc.tree.TinyTree;
import com.shuimin.base.struc.tree.Tree;

public class ActionTree extends TinyTree<Action> {
	public ActionTree() {
	}

	public ActionTree(Action action) {
		super(action);
		elem(action);
	}

	@Override
	public ActionTree elem(Action t) {
		super.name(_notNull(t._name));
		super.elem(t);
		return this;
	}

	@Override
	public ActionTree select(String name) {
		if (name.equals("/"))
			return (ActionTree) this.root();

		if (name.equals("."))
			return (ActionTree) this;

		if (name.equals(".."))
			return (ActionTree) this.parent();

		for (Tree<Action> res : children()) {
			if (res.name().equals(name))
				return (ActionTree) res;
		}
		return null;
	}

	@Override
	public ActionTree select(String[] names) {
		ActionTree cur = this;
		for (int i = 0; i < names.length; i++) {
			if (names[i].length() == 0)
				continue;
			cur = cur.select(names[i]);
			if (cur == null)
				break;
		}
		return cur;
	}

	@Override
	public ActionTree name(String name) {
		super.name(name);
		return this;
	}
	
}
