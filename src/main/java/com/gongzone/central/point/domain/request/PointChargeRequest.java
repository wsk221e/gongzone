package com.gongzone.central.point.domain.request;

import com.gongzone.central.point.payment.domain.Payment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class PointChargeRequest extends PointRequest {

	private Payment payment;

}
