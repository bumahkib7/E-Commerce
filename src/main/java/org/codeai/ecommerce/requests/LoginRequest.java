package org.codeai.ecommerce.requests;

import java.util.Optional;

public record LoginRequest(String username, String password, Optional<String> email) {
}
