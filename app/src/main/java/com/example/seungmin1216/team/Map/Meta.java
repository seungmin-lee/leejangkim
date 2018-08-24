package com.example.seungmin1216.team.Map;

public class Meta {
    private String total_count;
    private String pageable_count;
    private String is_end;

    public Meta(String total_count, String pageable_count, String is_end) {
        this.total_count = total_count;
        this.pageable_count = pageable_count;
        this.is_end = is_end;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getPageable_count() {
        return pageable_count;
    }

    public void setPageable_count(String pageable_count) {
        this.pageable_count = pageable_count;
    }

    public String getIs_end() {
        return is_end;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }
}
