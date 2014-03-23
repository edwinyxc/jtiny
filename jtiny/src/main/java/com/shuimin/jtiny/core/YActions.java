package com.shuimin.jtiny.core;


import com.shuimin.base.S;
import com.shuimin.base.f.Function;
import com.shuimin.base.f.Tuple;
import com.shuimin.base.struc.Cache;
import com.shuimin.jtiny.Y;

public class YActions {

    private final ActionTree yrt = new ActionTree().name(Y.config().name());

    private final Cache<String, ActionTree> cache
        = Cache.<String, ActionTree>lruCache(1000).onNotFound(
            (path) -> {
                ActionTree result = _getTree(path);
                if (result == null) {
                    throw new HttpException(
                        404, "[" + path + "] not found.");
                }
                return result;
            });

    public Y end() {
        return Y.get();
    }

    public ActionTree rt() {
        return yrt;
    }

    public static String[] splitPath(final String path) {

        return S._for(new Function._0<String[]>() {

            @Override
            public String[] apply() {
                if (path.equals("/")) {
                    return new String[]{"/"};
                }
                if (path.startsWith("/")) {
                    return path.substring(1).split("/");
                }
                return path.split("/");
            }

        }.apply()).<String>map((a) -> a.trim()).join();
    }

    private Tuple<ActionTree, String> _find_and_mkdirs(String path) {
        String[] paths = splitPath(path);
        S.echo("paths:" + S.dump(paths));
        ActionTree cur = yrt;
        for (int i = 0; i < paths.length; i++) {
            S.echo("name=" + paths[i]);
            ActionTree next = cur.select(paths[i]);
            S.echo("next=" + next);
            if (next == null) {
                if (i == paths.length - 1) {
                    next = cur;
                } else {
                    next = new ActionTree().name(paths[i]);
                    next.addTo(cur);
                }
            }
            cur = next;
        }
        S.echo("bind to " + cur.name());
        return Tuple._2(cur, paths[paths.length - 1]);
    }

    public YActions bind(String path, Action action) {
        Tuple<ActionTree, String> result = _find_and_mkdirs(path);
        if (result._b.equals("/")) {
            result._a.elem(action.name(result._b));
        } else {
            result._a.add(new ActionTree(action.name(result._b)));
        }
        return this;
    }

//	public YActions bind(String path, Controller controller) {
//		final ActionTree parent = _find_and_mkdirs(path)._a;
//		controller.name(parent.name());
//		S._for(controller._actions).each((Entry<String, Action> entry) -> {
//			parent.add(new ActionTree().elem(entry.getValue()));
//		});
//		return this;
//	}
    private ActionTree _getTree(String path) {
        ActionTree node = yrt.select(splitPath(path));
        Y.tree_node(node);
        return node;
    }

    public Action get(String path) {
        return cache.get(path).elem();
    }

}
