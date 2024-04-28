package com.zz.common.util;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;


@AllArgsConstructor
public class ZzTreeBuilder<T> {

    private final Function<T, Long> zzIdExtractor;
    private final Function<T, Long> zzParentIdExtractor;
    private final BiConsumer<T, List<T>> zzChildrenSetter;

    public List<T> zzBuildTree(List<T> zzAllNodes, Long zzParentId) {
        return zzAllNodes.stream()
                .filter(node -> (zzParentId == null && zzParentIdExtractor.apply(node) == null) ||
                        (zzParentId != null && zzParentId.equals(zzParentIdExtractor.apply(node))))
                .peek(node -> zzChildrenSetter.accept(node, zzBuildTree(zzAllNodes, zzIdExtractor.apply(node))))
                .collect(Collectors.toList());
    }
}
