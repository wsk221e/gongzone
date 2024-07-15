package com.gongzone.central.point.service.impl;

import com.gongzone.central.point.domain.PointHistory;
import com.gongzone.central.point.domain.request.PointRequest;
import com.gongzone.central.point.mapper.PointHistoryMapper;
import com.gongzone.central.point.mapper.PointMapper;
import com.gongzone.central.point.service.PointHistoryService;
import com.gongzone.central.utils.MySqlUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {


	private final PointMapper pointMapper;
	private final PointHistoryMapper pointHistoryMapper;


	/**
	 * 포인트 충전/인출 시도를 데이터베이스에 기록하고, 해당 기록의 고유 번호를 반환한다.
	 *
	 * @param memberPointNo 회원 포인트 번호
	 * @param request       포인트 변동 내역
	 * @return 포인트 내역 번호
	 */
	@Override
	public String insert(String memberPointNo, PointRequest request) {
		calculatePointUpdate(memberPointNo, request);

		String last = pointHistoryMapper.getLastHistoryPk();
		PointHistory pointHistory = PointHistory.builder()
												.pointHistoryNo(MySqlUtil.generatePrimaryKey(last))
												.memberPointNo(memberPointNo)
												.pointHistoryBefore(request.getPointBefore())
												.pointHistoryChange(request.getPointChange())
												.pointHistoryAfter(request.getPointBefore())  // 처음 insert 시 실패를 가정한다. 따라서 before 값 삽입
												.type(request.getChangeType())
												.build();
		pointHistoryMapper.insertPointHistory(pointHistory);

		return pointHistory.getPointHistoryNo();
	}

	/**
	 * 포인트 변동량, 변동 전/후를 계산한다.
	 *
	 * @param memberPointNo 회원 포인트 번호
	 * @param request       포인트 변동량
	 */
	@Override
	public void calculatePointUpdate(String memberPointNo, PointRequest request) {
		int current = pointMapper.getCurrentPoint(memberPointNo);
		int after = current + request.getPointChange();

		request.setPointBefore(current);
		request.setPointAfter(after);
	}

	/**
	 * 포인트 충전 상태를 성공으로 변경한다.
	 *
	 * @param historyNo   포인트내역번호
	 * @param pointCharge 포인트 변동 객체
	 */
	@Override
	public void updateSuccess(String historyNo, PointRequest pointCharge) {
		int pointHistoryAfter = pointCharge.getPointAfter();
		pointHistoryMapper.updateHistorySuccess(historyNo, pointHistoryAfter);
	}


	/**
	 * 회원의 (모든) 포인트 사용 내역을 반환한다.
	 *
	 * @param memberNo 회원 번호
	 * @param size     페이지 크기
	 * @param page     페이지 번호
	 * @return 포인트 사용내역 List
	 */
	@Override
	public List<PointHistory> getHistories(String memberNo, int size, int page) {
		String pointNo = pointMapper.getPointNo(memberNo);
		return pointHistoryMapper.getHistories(pointNo, size, page - 1);
	}

	/**
	 * 회원의 포인트 사용 내역을 반환한다.
	 *
	 * @param memberNo       회원 번호
	 * @param pointHistoryNo 포인트 내역 번호
	 * @return 포인트 사용내역
	 */
	@Override
	public PointHistory getHistory(String memberNo, String pointHistoryNo) {
		String pointNo = pointMapper.getPointNo(memberNo);
		return pointHistoryMapper.getHistory(pointNo, pointHistoryNo);
	}

	@Override
	public String insertHistory(String memberNo, PointRequest request) {
		String pointNo = pointMapper.getPointNo(memberNo);
		return insert(pointNo, request);
	}

	@Override
	public void updateHistorySuccess(String historyNo, PointRequest request) {
		updateSuccess(historyNo, request);
	}

}