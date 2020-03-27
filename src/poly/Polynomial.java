package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	private static Node reverse(Node input){
		Node result = null;
		while(input != null) {
			result = new Node(input.term.coeff, input.term.degree, result);
			input = input.next;
		}
		return result;
	}
	
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node sum = null;
		while(poly1 != null || poly2 != null) {
			if (poly1 == null && poly2 != null) {
				while (poly2 != null) {
					sum = new Node(poly2.term.coeff, poly2.term.degree, sum);
					poly2 = poly2.next;
				}
			}else if (poly2 == null && poly1 != null) {
				while (poly1 != null) {
					sum = new Node(poly1.term.coeff, poly1.term.degree, sum);
					poly1 = poly1.next;
				}
			}else if (poly1.term.degree == poly2.term.degree) {
				float combine = poly1.term.coeff+poly2.term.coeff;
				if (combine == 0) {
					poly1 = poly1.next;
					poly2 = poly2.next;
					continue;
				}
				sum = new Node(combine, poly1.term.degree, sum);
				poly1 = poly1.next;
				poly2 = poly2.next;
			}else if (poly1.term.degree < poly2.term.degree) {
				sum = new Node(poly1.term.coeff, poly1.term.degree, sum);
				poly1 = poly1.next;
			}else if (poly2.term.degree < poly1.term.degree) {
				sum = new Node(poly2.term.coeff, poly2.term.degree, sum);
				poly2 = poly2.next;
			}
		}
		sum = reverse(sum);
		return sum;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node product = null;
		Node prod = null;
		for(Node temp = poly1; temp != null; temp = temp.next) {
			for(Node temp2 = poly2; temp2 != null; temp2 = temp2.next) {
				product = new Node(temp2.term.coeff*temp.term.coeff, temp2.term.degree+temp.term.degree, product);
			}
			product = reverse(product);
			prod = add(product, prod);
			product = null;
		}
		return prod;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float result = 0.0f;
		for (Node temp = poly; temp != null; temp = temp.next) {
			result += temp.term.coeff*Math.pow(x, temp.term.degree);	
		}
		return result;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
}
