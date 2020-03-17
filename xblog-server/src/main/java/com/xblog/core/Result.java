package com.xblog.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 统一API响应结果封装
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    public Result() {
		super();
	}

	private static final long serialVersionUID = 171791176757477130L;

    private int code;
    private String msg;
    private T data;
    private Long total;

    public Result(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg, T data, Long total){
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
