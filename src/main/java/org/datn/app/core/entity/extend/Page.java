package org.datn.app.core.entity.extend;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Page {
    protected Optional<Integer> page;
    protected Optional<Integer> size;
    public Page() {
        if(this.page == null) {
            this.page = Optional.of(0);
        }
        if(this.size == null) {
            this.size = Optional.of(10);
        }
    }
}
