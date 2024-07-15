package com.gongzone.central.board.mapper;

import com.gongzone.central.board.domain.Board;
import com.gongzone.central.board.domain.BoardReply;
import com.gongzone.central.board.domain.BoardSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void updatePartyMember(String boardNo, Board board);
    void updateParty(String boardNo, Board board);
    void updateLocation(String boardNo, Board board);
    void updateBoard(String boardNo, Board board);

    List<Board> getBoardInfo(String boardNo);

    void deleteWish(String boardNo, String memberNo);
    void insertWish(String boardNo, String memberNo);

    void updateViewCount(String boardNo);

    void insertBoard(Board board);
    void insertLocation(Board board);
    void insertFileRelation(Board board);
    void insertParty(Board board);
    void insertPartyMember(Board board);

    List<Board> getBoardList(BoardSearchRequest request);
    List<BoardReply> getBoardReplyList(String boardNo);
    int getBoardWish(String memberNo, String boardNo);
}
