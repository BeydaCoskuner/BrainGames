package com.elma.braingames.puzzles.tangram.model;

import com.badlogic.gdx.graphics.Color;

public class TangramBoard {

    public static final int PIECE_COUNT = 7;

    private final TangramPiece[] pieces;


    public TangramBoard() {

        pieces =
            new TangramPiece[PIECE_COUNT];

        createPieces();
    }


    private void createPieces() {

        pieces[0] =
            new TangramPiece(
                0,
                TangramPiece.PieceType.LARGE_TRIANGLE_1,
                new Color(
                    0.95f,
                    0.20f,
                    0.08f,
                    1f
                )
            );


        pieces[1] =
            new TangramPiece(
                1,
                TangramPiece.PieceType.LARGE_TRIANGLE_2,
                new Color(
                    0.10f,
                    0.45f,
                    0.90f,
                    1f
                )
            );


        pieces[2] =
            new TangramPiece(
                2,
                TangramPiece.PieceType.MEDIUM_TRIANGLE,
                new Color(
                    0.20f,
                    0.75f,
                    0.30f,
                    1f
                )
            );


        pieces[3] =
            new TangramPiece(
                3,
                TangramPiece.PieceType.SMALL_TRIANGLE_1,
                new Color(
                    0.95f,
                    0.75f,
                    0.05f,
                    1f
                )
            );


        pieces[4] =
            new TangramPiece(
                4,
                TangramPiece.PieceType.SMALL_TRIANGLE_2,
                new Color(
                    0.55f,
                    0.25f,
                    0.80f,
                    1f
                )
            );


        pieces[5] =
            new TangramPiece(
                5,
                TangramPiece.PieceType.SQUARE,
                new Color(
                    0.95f,
                    0.35f,
                    0.05f,
                    1f
                )
            );


        pieces[6] =
            new TangramPiece(
                6,
                TangramPiece.PieceType.PARALLELOGRAM,
                new Color(
                    0.05f,
                    0.70f,
                    0.70f,
                    1f
                )
            );
    }


    public TangramPiece getPiece(
        int index
    ) {

        if (
            index < 0 ||
                index >= PIECE_COUNT
        ) {

            return null;
        }

        return pieces[index];
    }


    public TangramPiece[] getPieces() {

        return pieces;
    }


    public boolean areAllPiecesPlaced() {

        for (
            TangramPiece piece :
            pieces
        ) {

            if (!piece.isPlaced()) {

                return false;
            }
        }

        return true;
    }
}
