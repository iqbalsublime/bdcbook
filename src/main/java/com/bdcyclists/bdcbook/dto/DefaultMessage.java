package com.bdcyclists.bdcbook.dto;

/**
 * This DTO is created to carry simple message to a page.
 *
 * @author Bazlur Rahman Rokon
 * @date 1/14/15.
 */
public class DefaultMessage {
    private String title;
    private String message;

    public DefaultMessage() {
    }

    public DefaultMessage(String title) {
        this.title = title;
    }

    public DefaultMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
