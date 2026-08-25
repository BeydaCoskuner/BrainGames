package com.elma.braingames.puzzles.tangram.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.puzzles.tangram.layout.TangramLayout;
import com.elma.braingames.puzzles.tangram.manager.TangramGameManager;
import com.elma.braingames.puzzles.tangram.model.TangramPiece;

public class TangramInputHandler extends InputAdapter {

    private final Viewport worldViewport;
    private final TangramGameManager gameManager;
    private final TangramLayout layout;

    private final Vector3 touch =
        new Vector3();

    private int selectedPiece = -1;

    private float dragOffsetX;
    private float dragOffsetY;

    private long lastTapTime = 0L;
    private int lastTappedPiece = -1;

    private static final long DOUBLE_TAP_TIME = 300L;


    public TangramInputHandler(
        Viewport worldViewport,
        TangramGameManager gameManager,
        TangramLayout layout
    ) {

        this.worldViewport = worldViewport;
        this.gameManager = gameManager;
        this.layout = layout;
    }


    @Override
    public boolean touchDown(
        int screenX,
        int screenY,
        int pointer,
        int button
    ) {

        if (!gameManager.isPlaying()) {
            return false;
        }

        touch.set(
            screenX,
            screenY,
            0
        );

        worldViewport.unproject(touch);

        int pieceId =
            findPiece(
                touch.x,
                touch.y
            );

        if (pieceId == -1) {
            return false;
        }

        long currentTime =
            System.currentTimeMillis();

        if (
            pieceId == lastTappedPiece
                &&
                currentTime - lastTapTime
                    <= DOUBLE_TAP_TIME
        ) {

            gameManager.rotatePiece(
                pieceId
            );

            lastTapTime = 0L;
            lastTappedPiece = -1;

            return true;
        }

        lastTapTime = currentTime;
        lastTappedPiece = pieceId;

        selectedPiece = pieceId;

        TangramPiece piece =
            gameManager
                .getBoard()
                .getPiece(pieceId);

        if (piece == null) {
            return false;
        }

        dragOffsetX =
            piece.getX() - touch.x;

        dragOffsetY =
            piece.getY() - touch.y;

        return true;
    }


    @Override
    public boolean touchDragged(
        int screenX,
        int screenY,
        int pointer
    ) {

        if (selectedPiece == -1) {
            return false;
        }

        touch.set(
            screenX,
            screenY,
            0
        );

        worldViewport.unproject(touch);

        float newX =
            touch.x + dragOffsetX;

        float newY =
            touch.y + dragOffsetY;

        gameManager.movePiece(
            selectedPiece,
            newX,
            newY
        );

        return true;
    }


    @Override
    public boolean touchUp(
        int screenX,
        int screenY,
        int pointer,
        int button
    ) {

        if (selectedPiece == -1) {
            return false;
        }

        int releasedPiece =
            selectedPiece;

        selectedPiece = -1;

        gameManager.releasePiece(
            releasedPiece
        );

        return true;
    }


    private int findPiece(
        float x,
        float y
    ) {

        TangramPiece[] pieces =
            gameManager
                .getBoard()
                .getPieces();

        for (
            int i = pieces.length - 1;
            i >= 0;
            i--
        ) {

            TangramPiece piece =
                pieces[i];

            if (piece.isPlaced()) {
                continue;
            }

            float width =
                layout.getPieceWidth(
                    piece.getType()
                );

            float height =
                layout.getPieceHeight(
                    piece.getType()
                );

            float halfWidth =
                width / 2f;

            float halfHeight =
                height / 2f;

            if (
                x >= piece.getX() - halfWidth
                    &&
                    x <= piece.getX() + halfWidth
                    &&
                    y >= piece.getY() - halfHeight
                    &&
                    y <= piece.getY() + halfHeight
            ) {

                return piece.getId();
            }
        }

        return -1;
    }
}
