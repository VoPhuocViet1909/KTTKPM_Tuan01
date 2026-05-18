package com.javabuilder.searchservice.service;

import com.javabuilder.searchservice.document.ProductDocument;
import com.javabuilder.searchservice.dto.request.SearchRequest;
import com.javabuilder.searchservice.dto.response.PageResponse;
import com.javabuilder.searchservice.dto.response.AggregationResponse;

public interface ProductDocumentService {
    void saveProductDocument(ProductDocument document);
    void deleteProductDocument(String id);
    PageResponse<ProductDocument> getAllWithSearch(int page, int size, SearchRequest request);
    AggregationResponse getAggregations(SearchRequest request);
}