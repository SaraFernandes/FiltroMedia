/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtromedia;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

// PARA  interface gráfica
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.io.File;

// PARA LEITURA DA IMAGEM
import javax.imageio.ImageIO;

/**
 * *********************************************************
 */
/* PI - PROCESSAMENTO DE IMAGNES                            */
/* UFT - UNIVERSIDADE FEDERAL DO TOCANTINS 2015             */
/* PROFESSOR: GLENDA BOTELHO                                */
/* SEGUNDO TRABALHO                                         */
/* TAYLA SOUSA                                              */
/* SARA FERNANDES                                           */
/* DATA DE ENTREGA : 24/11/2015                             */
/*                                                          */
/**
 * *********************************************************
 */
public class FiltroMedia extends JFrame {

    public static void main(String args[]) {

        // Mostra JFrame 
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Carrega e Visualiza imagem original
        String ima_name_in = "/home/sara/NetBeansProjects/FiltroMedia/src/filtromedia/stitch.jpg";
        // Carrega e Visualiza imagem com Filtro
        String ima_name_out = "/home/sara/NetBeansProjects/FiltroMedia/src/filtromedia/ComFiltro.jpg";
        BufferedImage ima_in, ima_out;

        ima_in = CarregaImagem(ima_name_in);

        FiltroMedia frame1 = new FiltroMedia();

        frame1.MostraImagem(ima_in, ima_name_in);

        // Carrega e processa imagem original e mostra imagem processada
        ima_out = ProcessaImagem(ima_in);

        FiltroMedia frame2 = new FiltroMedia();

        frame2.MostraImagem(ima_out, ima_name_out);

        SalvaImagem(ima_out, ima_name_out);
    }

    public static BufferedImage CarregaImagem(String image_name) {
        // Associa objeto BufferedImage com <arquivo_imagem>
        BufferedImage ima_in = null;

        // Carrega imagem
        File file = new File(image_name);
        try {
            ima_in = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("IMAGEM '" + image_name + "' NÃO EXISTE.");
            System.exit(0);
        }

        System.out.println("Nome da Imagem: " + image_name + " Tipo da Imagem: " + ima_in.getType());
        System.out.println("Tamanho da Imagem: Colunas " + ima_in.getWidth() + " Linhas " + ima_in.getHeight());

        return ima_in;

    }

    public static void SalvaImagem(BufferedImage dest, String image_name) {

        try {

            ImageIO.write(dest, "jpg", new File(image_name));

        } catch (Exception e) {

            System.out.println("PROBLEMA AO GRAVAR ARQUIVO.");
            System.exit(0);
        }
    }

    public static BufferedImage ProcessaImagem(BufferedImage ima_in) {

        // Associa objeto BufferedImage com <arquivo_imagem> níveis de cinza (10)
        //BufferedImage ima_out  = new BufferedImage(ima_in.getWidth(),ima_in.getHeight(),BufferedImage.TYPE_BYTE_GRAY); // 10 - TYPE_BYTE_GRAY
        BufferedImage ima_out = new BufferedImage(ima_in.getWidth(), ima_in.getHeight(), ima_in.getType()); // 10 - TYPE_BYTE_GRAY
        //BufferedImage ima_out = ima_in;
        // Recupera matriz das imagens de entrada e saida

        Raster raster = ima_in.getRaster(); // declara e instancia objeto raster só para leitura
        WritableRaster wraster = ima_out.getRaster(); // declara e instancia objeto raster para escrita

        // Processa valores da imagem de entrada e armazena na imagem de saida
        int r, g, b;
        double valornr, valorng, valornb;
        double p[][] = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = 1.0 / 9.0;
            }
        }

        for (int y = 1; y < ima_in.getHeight() - 1; y++) {
            for (int x = 1; x < ima_in.getWidth() - 1; x++) {

                r = raster.getSample(x, y, 0);  // lê dado da banda 0 da imagem de entrada
                g = raster.getSample(x, y, 1);  // lê dado da banda 1 da imagem de entrada
                b = raster.getSample(x, y, 2);  // lê dado da banda 2 da imagem de entrada

//			Filtro de média
                valornr = p[0][0] * (double) raster.getSample(x - 1, y - 1, 0) + p[0][1] * (double) raster.getSample(x, y - 1, 0)
                        + p[0][2] * (double) raster.getSample(x + 1, y - 1, 0)
                        + p[1][0] * (double) raster.getSample(x - 1, y, 0) + p[1][1] * (double) raster.getSample(x, y, 0)
                        + p[1][2] * (double) raster.getSample(x + 1, y, 0)
                        + p[2][0] * (double) raster.getSample(x - 1, y + 1, 0) + p[2][1] * (double) raster.getSample(x, y + 1, 0)
                        + p[2][2] * (double) raster.getSample(x + 1, y + 1, 0);

                valorng = p[0][0] * (double) raster.getSample(x - 1, y - 1, 1) + p[0][1] * (double) raster.getSample(x, y - 1, 1)
                        + p[0][2] * (double) raster.getSample(x + 1, y - 1, 1)
                        + p[1][0] * (double) raster.getSample(x - 1, y, 1) + p[1][1] * (double) raster.getSample(x, y, 1)
                        + p[1][2] * (double) raster.getSample(x + 1, y, 1)
                        + p[2][0] * (double) raster.getSample(x - 1, y + 1, 1) + p[2][1] * (double) raster.getSample(x, y + 1, 1)
                        + p[2][2] * (double) raster.getSample(x + 1, y + 1, 1);

                valornb = p[0][0] * (double) raster.getSample(x - 1, y - 1, 2) + p[0][1] * (double) raster.getSample(x, y - 1, 2)
                        + p[0][2] * (double) raster.getSample(x + 1, y - 1, 2)
                        + p[1][0] * (double) raster.getSample(x - 1, y, 2) + p[1][1] * (double) raster.getSample(x, y, 2)
                        + p[1][2] * (double) raster.getSample(x + 1, y, 2)
                        + p[2][0] * (double) raster.getSample(x - 1, y + 1, 2) + p[2][1] * (double) raster.getSample(x, y + 1, 2)
                        + p[2][2] * (double) raster.getSample(x + 1, y + 1, 2);

                wraster.setSample(x, y, 0, (int) (valornr + .5));
                wraster.setSample(x, y, 1, (int) (valorng + .5));
                wraster.setSample(x, y, 2, (int) (valornb + .5));

            }
        }

        return ima_out;
    }    

	public void MostraImagem(BufferedImage ima, String image_name) {

        // Define GUI com objetos do Swing
        JLabel lsrc2 = new JLabel(new ImageIcon(ima));
        getContentPane().add(new JScrollPane(lsrc2));

        // Atribui nome e tamanho ao frame
        setTitle("Imagem Preocessada 2D: =>" + image_name);
        setSize(ima.getWidth() + 40, ima.getHeight() + 40);

        setVisible(true);

        // Encerra a aplicação clicando no "close"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    

}
