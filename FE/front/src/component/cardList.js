import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getCardsFromNathan } from "../editor/api";
import Card from "./card";
import {
  CardProvider,
  useCardDispatch,
  useCardState,
} from "../editor/cardContext";
import { CardListConainer } from "../styled-component/cardStyle";
import { ContentAddIndex } from "./contentExtraIndex";

export function CardList() {
  const param = useParams();
  const noteId = parseInt(param.id);
  const cardPack = useCardState();
  const cardDispatch = useCardDispatch();
  const [noCardAlert, setNoCardAlert] = useState(false);

  useEffect(() => {
    getCardsFromNathan(cardDispatch, noteId);
    return console.log("카드 받는 중");
  }, []);

  if (noCardAlert) {
    return <div>카드없음</div>;
  }

  return (
    <>
      <CardListConainer>
        {cardPack.length > 0 ? (
          cardPack.map((item, index) => <Card key={index} data={item} />)
        ) : (
          <div>카드 받아오는 중...</div>
        )}
      </CardListConainer>
    </>
  );
}
