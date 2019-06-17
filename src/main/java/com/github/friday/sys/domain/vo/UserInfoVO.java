package com.github.friday.sys.domain.vo;

import java.util.List;

public class UserInfoVO {
    // 用户名
    private String name;
    // 头像地址
    private String avatar;
    // 角色
    private List<String> roles;
    // 路由
    private List<Routes> routes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }

    // 路由菜单
    public static class Routes {
        // 是否隐藏
        private boolean hidden;
        // 是否一直显示在菜单栏
        private boolean alwaysShow;
        // 重定向地址
        private String redirect;
        // 名称
        private String name;
        // 路径
        private String path;
        // 信息
        private Meta meta;
        // 子路由
        private List<Routes> children;

        public boolean isHidden() {
            return hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        public boolean isAlwaysShow() {
            return alwaysShow;
        }

        public void setAlwaysShow(boolean alwaysShow) {
            this.alwaysShow = alwaysShow;
        }

        public String getRedirect() {
            return redirect;
        }

        public void setRedirect(String redirect) {
            this.redirect = redirect;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public List<Routes> getChildren() {
            return children;
        }

        public void setChildren(List<Routes> children) {
            this.children = children;
        }
    }

    public static class Meta {
        // 标题
        private String title;
        // 图标
        private String icon;
        // 有权访问的角色
        private List<String> roles;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}


