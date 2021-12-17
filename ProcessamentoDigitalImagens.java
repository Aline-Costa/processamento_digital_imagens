import java.util.ArrayList;
import java.util.stream.Collectors;

import Lista06.ImagemDigital;

public class ProcessamentoDigitalImagens {
	
	public static int [][] multiplicacao (int img1[][],int img2[][]) {
		
		int[][] imgMult = new int [img1.length][img1[0].length];
		
		for (int i = 0; i < imgMult.length; i++) {
			for(int j = 0; j < imgMult[0].length; j++) {
				imgMult[i][j] = img1[i][j] * img2[i][j] / 255;
				
			}
		}
		return imgMult;
	}
	
	public static void correcaoEscala(int img [][]) {
		int max = 0;
		int min = 255;
		
		for (int i = 0; i < img.length; i++) {
			for(int j = 0; j < img[0].length; j++) {
				max = Math.max(max, img[i][j]);
				min = Math.min(min, img[i][j]);
			}
		}
		
		for (int i = 0; i < img.length; i++) {
			for(int j = 0; j < img[0].length; j++) {
				img[i][j] = (255*(img[i][j] - min)) / (max - min);
			}
		}
	}
	
	
	public static void correcaoEscala3D(int[][][] img) {

		int max = 0;
		int min = 255;

		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				for (int k = 0; k < img[0][0].length; k++) {
					max = Math.max(max, img[i][j][k]);
					min = Math.min(min, img[i][j][k]);
				}
			}
		}

		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				for (int k = 0; k < img[0][0].length; k++) {
					img[i][j][k] = 255 * (img[i][j][k] - min) / (max - min);
				}
			}
		}
	}
	
	
	public static void transformacaoGama(int[][][] img, double valorGama, String nome) {
		
		int[][][] imgTransformada = new int[img.length][img[0].length][img[0][0].length];
		double c = 0;
		int L = 255;
		
		c = Math.pow((L - 1), (1 - valorGama));
		
		for (int j = 0; j < img.length; j++) {
			for (int k = 0; k < img[0].length; k++) {
				for (int l = 0; l < img[0][0].length; l++) {
					imgTransformada[j][k][l] = (int) (c * Math.pow(img[j][k][l], valorGama));
				}
			}
		}
		ImagemDigital.correcaoEscala3D(imgTransformada);
		ImagemDigital.plotarImagemCor(imgTransformada, nome + " - Transformação Gama = " + valorGama);
	}
	

	public static int [][] negativo(int[][] img) {
		
		int[][] imgNegativo =  new int[img.length][img[0].length];
		int l = 255;
		
		for (int i = 0; i < imgNegativo.length; i++) {
			for (int j = 0; j < imgNegativo[0].length; j++) {
				imgNegativo[i][j] = l - 1 - img[i][j];
			}
		}
		ImagemDigital.correcaoEscala(imgNegativo);
		return imgNegativo;
		
	}
	
	
	public static int [][] filtroMedia(int[][]img, int n) {
		
		int [][] imgFiltro =  new int[img.length][img[0].length];
		
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				int soma = 0;
				int cont= 0;
				for(int k = 0; k < n; k++) {
					for(int l = 0; l < n; l++) {
						if(i + k >= 0 && i + k < img.length && j + l >= 0 && j + l < img[0].length) {
							soma = soma + img[i+k][j+l];
							cont++;
						}
					}
				}
				imgFiltro[i][j] = soma/cont;
			}
		}
		return imgFiltro;
	}
	
	
	public static int [][] filtroMediana(int[][]img, int n) {
		
		int [][] imgFiltro =  new int[img.length][img[0].length];
		
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				ArrayList<Integer> lista = new ArrayList<Integer>();
				ArrayList<Integer> listateste = new ArrayList<Integer>();
				for(int k = 0; k < n; k++) {
					for(int l = -1; l < n; l++) {
						if(i + k >= 0 && i + k < img.length && j + l >= 0 && j + l < img[0].length) {
							lista.add(img[i+k][j+l]);
						}
					}
				}
				
				ArrayList<Integer> listaOrdenada = (ArrayList<Integer>) lista.stream().sorted().collect(Collectors.toList());
				if(listaOrdenada.size() %2 != 0 ) {
					imgFiltro[i][j] = listaOrdenada.get(listaOrdenada.size()/2);
				}else {
					imgFiltro[i][j] = (listaOrdenada.get((listaOrdenada.size()/2) - 1) + listaOrdenada.get(listaOrdenada.size()/2)) / 2;
				}
				
			}
		}
		return imgFiltro;
	}
	
	
	public static int mediaImagem(int[][] img) {
		
		int soma = 0;
		int cont = 0;
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				soma += img[i][j];
				cont++;
			}
		}
		return soma / cont;
	}
	
	
	public static int calculaLimiar(int tInicial, int img[][]) {
		
		int diferenca;
		
		do {
			int somaG1 = 0;
			int somaG2 = 0;
			int contG1 = 0;
			int contG2 = 0;
			for (int i = 0; i < img.length; i++) {
				for (int j = 0; j < img[0].length; j++) {
					if(img[i][j] > tInicial) {
						somaG1 = somaG1 + img[i][j];
						contG1++;
					}else {
						somaG2 = somaG2 + img[i][j];
						contG2++;
					}
				}
			}
			int mediaG1 = somaG1 /contG1;
			int mediaG2 = somaG2 /contG2;
			
			int tNovo = (mediaG1 + mediaG2) / 2;
			diferenca = Math.abs(tInicial - tNovo);
			tInicial = tNovo;
			
		}while(diferenca > 0);
		
		return tInicial;
	}
	
	
	public static int[][] limiarizacaoSemLimiarDefinido(int img[][]){
		
		int[][] imgLimiarizada = new int[img.length][img[0].length];
		
		int tInicial = mediaImagem(img);
		int limiar = calculaLimiar(tInicial, img);
		
		for (int i = 0; i < imgLimiarizada.length; i++) {
			for (int j = 0; j < imgLimiarizada[0].length; j++) {
				if(img[i][j] > limiar) {
					imgLimiarizada[i][j] = 255;
				}else {
					imgLimiarizada[i][j] = 0;
				}
			}
		}
		return imgLimiarizada;
	}

	public static int[][] limiarizacaoComLimiarDefinido(int img[][], int limiar){
		
		int[][] imgLimiarizada = new int[img.length][img[0].length];
		
		for (int i = 0; i < imgLimiarizada.length; i++) {
			for (int j = 0; j < imgLimiarizada[0].length; j++) {
				if(img[i][j] > limiar) {
					imgLimiarizada[i][j] = 255;
				}else {
					imgLimiarizada[i][j] = 0;
				}
			}
		}
		return imgLimiarizada;
	}
	
	public static int [][] filtragemHighBoost(int img[][], int n, int k){
		
		int[][] altoReforco = new int [img.length][img[0].length];
		int[][] borrada = filtroMedia(img, n);
		int[][] mascara = new int [img.length][img[0].length];
		
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				mascara[i][j] =  img[i][j] - borrada[i][j];
			}
		}
		
		ImagemDigital.correcaoEscala(mascara);
		
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				altoReforco[i][j] =  img[i][j] + k * mascara[i][j];
			}
		}
		
		ImagemDigital.correcaoEscala(altoReforco);
		return altoReforco;
	}
	
	public static int [][] mascaraNitidez(int img[][], int n){
		
		int[][] altoReforco = new int [img.length][img[0].length];
		int[][] borrada = filtroMedia(img, n);
		int[][] mascara = new int [img.length][img[0].length];
		
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				mascara[i][j] =  img[i][j] - borrada[i][j];
			}
		}
		
		ImagemDigital.correcaoEscala(mascara);
		
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				altoReforco[i][j] =  img[i][j] + mascara[i][j];
			}
		}
		
		ImagemDigital.correcaoEscala(altoReforco);
		return altoReforco;
	}
	
	public static int [][] gradientesDiagonaisRoberts(int img[][]){
			
		int[][] img_filtro = new int [img.length][img[0].length];
		
		for (int i = 1; i < img.length -1; i++) {
			for (int j = 1; j < img[0].length-1; j++) {
				img_filtro[i][j] = (int) Math.sqrt((Math.pow(img[i][j]-img[i-1][j+1], 2) + Math.pow(img[i][j]-img[i-1][j-1], 2)));
			}
		}
		return img_filtro;
	}
	
	public static int [][] laplaciano_negativo(int img[][]){
		
		int[][] imgLaplaciano = new int [img.length][img[0].length];
		
		for (int i = 1; i < img.length -1; i++) {
			for (int j = 1; j < img[0].length-1; j++) {
				imgLaplaciano[i][j] = img[i+1][j] + img[i-1][j] + img[i][j+1] + img[i][j-1] - 4*img[i][j];
			}
		}
		ProcessamentoDigitalImagens.correcaoEscala(imgLaplaciano);
		
		return imgLaplaciano;
	}
	
	public static int [][] laplacianoPositivo4(int img[][]){
		
		int[][] laplaciano = new int [img.length][img[0].length];
		
		for (int i = 1; i < img.length -1; i++) {
			for (int j = 1; j < img[0].length-1; j++) {
				int filtro_i = 2*img[i][j] - img[i+1][j] - img[i-1][j];
			    int filtro_j = 2*img[i][j] - img[i][j+1] - img[i][j-1];
			    laplaciano[i][j] = filtro_i  + filtro_j;
			}
		}
		ProcessamentoDigitalImagens.correcaoEscala(laplaciano);
		
		return laplaciano;
	}
	
	public static int [][] laplacianoPositivo8(int img[][]){
		
		int[][] laplaciano = new int [img.length][img[0].length];
		
		for (int i = 1; i < img.length -1; i++) {
			for (int j = 1; j < img[0].length-1; j++) {
				int filtro_i = 4*img[i][j] - img[i+1][j] - img[i-1][j] - img[i-1][j-1]-img[i-1][j+1];
			    int filtro_j = 4*img[i][j] - img[i][j+1] - img[i][j-1] -  img[i+1][j-1]-img[i+1][j-1];
			    laplaciano[i][j] = filtro_i  + filtro_j;
				
			}
		}
		ProcessamentoDigitalImagens.correcaoEscala(laplaciano);
		
		return laplaciano;
	}
	
	public static int [][] sopel(int img[][]){
		
		int[][] sopel = new int [img.length][img[0].length];
		
		for (int i = 1; i < img.length -1; i++) {
			for (int j = 1; j < img[0].length-1; j++) {
				int sx =  2*img[i+1][j] - 2*img[i-1][j] - img[i-1][j-1] - img[i-1][j+1] + img[i+1][j-1] + img[i+1][j+1];
			    int sy =  2*img[i][j+1] - 2*img[i][j-1] - img[i-1][j-1] + img[i-1][j+1] - img[i+1][j-1] + img[i+1][j+1];
			    sopel[i][j] = (int) Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2));
			}
		}
		ProcessamentoDigitalImagens.correcaoEscala(sopel);
		
		return sopel;
	}
}
