package foorun.unieat.api.model.base.security;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.common.rules.ManagedStatusType;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public abstract class UniEatUserDetails extends UniEatBaseTimeEntity implements UserDetails {
    public abstract LocalDateTime getExpiredDate();
    public abstract LocalDateTime getLockedDate();

    public abstract ManagedStatusType getStatus();
}