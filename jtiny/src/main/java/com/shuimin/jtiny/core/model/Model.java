package com.shuimin.jtiny.core.model;

import java.util.HashMap;
import java.util.Map;

public class Model {

	final protected Map<String, ModelField> _fields = new HashMap<>();
	final protected String _name;

	private Model withFields(Map<String, ModelField> protoFields) {
		_fields.clear();
		_fields.putAll(protoFields);
		return this;
	}

	final static Model createFromProto(Model proto) {
		return new Model(proto._name).withFields(proto._fields);
	}

	final static Model createEmpty(String name) {
		return new Model(name);
	}

	private Model(String name) {
		_name = name;
	}

	public Model set(String name, Object value) {
		ModelField field;
		if ((field = _fields.get(name)) != null) {
			field.value = value;
		}
		return this;
	}

	public Model field(String name, int type, Object initValue) {
		if (_fields.get(name) == null) {
			_fields.put(name, new ModelField(type, name).val(initValue));
		}
		return this;
	}

	public Object get(String key) {
		ModelField mf;
		return (mf = _fields.get(key)) != null ? mf.value : null;
	}

}
