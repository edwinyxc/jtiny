package com.shuimin.jtiny.core;

import com.shuimin.base.S;
import com.shuimin.base.f.Function;
import com.shuimin.base.f.Tuple;
import com.shuimin.base.struc.Cache;
import com.shuimin.jtiny.Y;

public class YResources {

    private final ResourceTree yrt = new ResourceTree().name(Y.config().name());

    private final Cache<String, ResourceTree> cache
        = Cache.<String, ResourceTree>lruCache(1000).onNotFound(
            (path) -> {
                ResourceTree result = _getTree(path);
                if (result == null) {
                    throw new HttpException(
                        404, "[" + path + "] not found.");
                }
                return result;
            });

    public Y end() {
        return Y.get();
    }

    public ResourceTree rt() {
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

    private Tuple<ResourceTree, String> _find_and_mkdirs(String path) {
        String[] paths = splitPath(path);
        S.echo("paths:" + S.dump(paths));
        ResourceTree cur = yrt;
        for (int i = 0; i < paths.length; i++) {
            S.echo("name=" + paths[i]);
            ResourceTree next = cur.select(paths[i]);
            S.echo("next=" + next);
            if (next == null) {
                if (i == paths.length - 1) {
                    next = cur;
                } else {
                    next = new ResourceTree().name(paths[i]);
                    next.addTo(cur);
                }
            }
            cur = next;
        }
        S.echo("bind to " + cur.name());
        return Tuple._2(cur, paths[paths.length - 1]);
    }

    public YResources bind(String path, Resource res) {
        Tuple<ResourceTree, String> result = _find_and_mkdirs(path);
        if (result._b.equals("/")) {
            result._a.elem(res.name(result._b));
        } else {
            result._a.add(new ResourceTree(res.name(result._b)));
        }
        return this;
    }

    public YResource bind() {

    }

//	public YActions bind(String path, Controller controller) {
//		final ActionTree parent = _find_and_mkdirs(path)._a;
//		controller.name(parent.name());
//		S._for(controller._actions).each((Entry<String, Action> entry) -> {
//			parent.add(new ActionTree().elem(entry.getValue()));
//		});
//		return this;
//	}
    private ResourceTree _getTree(String path) {
        ResourceTree node = yrt.select(splitPath(path));
        Y.tree_node(node);
        return node;
    }

    public Resource get(String path) {
        return cache.get(path).elem();
    }

}
