package com.usernamelist.entities;

import java.util.List;

public class Result<T, A extends List<String>> {

	private T t;
	private A a;

	public Result(T t, A a) {
		this.t = t;
		this.a = a;
	}

	/**
	 * @return the t
	 */
	public T getT() {
		return this.t;
	}

	/**
	 * @param to
	 *            the t to set
	 */
	public void setT(T t) {
		this.t = t;
	}

	/**
	 * @return the a
	 */
	public A getA() {
		return this.a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setA(A a) {
		this.a = a;
	}
}
