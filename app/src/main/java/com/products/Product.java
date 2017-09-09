package com.products;


public class Product {
	private String p_name,p_price, p_brief,p_image,p_info,likes_count,is_like;

	public Product() {
	}

	public Product(String p_name, String p_price, String p_brief,String p_image,String p_info,String likes_count,String is_like) {
		this.p_name = p_name;
		this.p_brief = p_brief;
		this.p_price = p_price;
        this.p_image = p_image;
        this.p_info = p_info;
		this.likes_count=likes_count;
		this.is_like=is_like;

	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getP_brief() {
		return p_brief;
	}

	public void setP_brief(String p_brief) {
		this.p_brief = p_brief;
	}

	public String getP_price() {
		return p_price;
	}

	public void setP_price(String p_name) {
		this.p_price = p_price;
	}


    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }


    public String getP_info() {
        return p_info;
    }

    public void setP_info(String p_info) {
        this.p_info = p_info;
    }


	public String getLikes_count() {
		return likes_count;
	}

	public void setLikes_count(String likes_count) {
		this.likes_count = likes_count;
	}


    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }


}
