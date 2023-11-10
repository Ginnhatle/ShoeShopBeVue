package org.datn.app.core.entity.extend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO extends Page{
    private Long userId;
}
