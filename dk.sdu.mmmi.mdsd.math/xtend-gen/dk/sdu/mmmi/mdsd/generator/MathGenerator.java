/**
 * generated by Xtext 2.25.0
 */
package dk.sdu.mmmi.mdsd.generator;

import com.google.common.collect.Iterators;
import dk.sdu.mmmi.mdsd.math.Div;
import dk.sdu.mmmi.mdsd.math.Expression;
import dk.sdu.mmmi.mdsd.math.MathNumber;
import dk.sdu.mmmi.mdsd.math.Minus;
import dk.sdu.mmmi.mdsd.math.Mult;
import dk.sdu.mmmi.mdsd.math.Parenthesis;
import dk.sdu.mmmi.mdsd.math.Plus;
import dk.sdu.mmmi.mdsd.math.Program;
import dk.sdu.mmmi.mdsd.math.VarBinding;
import dk.sdu.mmmi.mdsd.math.VariableUse;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class MathGenerator extends AbstractGenerator {
  private static Map<String, Integer> variables;
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    final Program program = Iterators.<Program>filter(resource.getAllContents(), Program.class).next();
    String _name = program.getName();
    String _plus = ("math_expression/" + _name);
    String _plus_1 = (_plus + ".java");
    fsa.generateFile(_plus_1, this.compile(program));
  }
  
  public CharSequence compile(final Program program) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package math_expression;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _name = program.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      EList<VarBinding> _variables = program.getMathExps().getVariables();
      for(final VarBinding varBinding : _variables) {
        _builder.append("\t");
        _builder.append("public int ");
        String _name_1 = varBinding.getName();
        _builder.append(_name_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void compute() {");
    _builder.newLine();
    {
      EList<VarBinding> _variables_1 = program.getMathExps().getVariables();
      for(final VarBinding varBinding_1 : _variables_1) {
        _builder.append("\t\t");
        String _name_2 = varBinding_1.getName();
        _builder.append(_name_2, "\t\t");
        _builder.append(" = ");
        String _resolve = this.resolve(varBinding_1.getExpression());
        _builder.append(_resolve, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public String resolve(final Expression expression) {
    String output = "";
    boolean _matched = false;
    if (expression instanceof MathNumber) {
      _matched=true;
      String _output = output;
      int _value = ((MathNumber)expression).getValue();
      output = (_output + Integer.valueOf(_value));
    }
    if (!_matched) {
      if (expression instanceof Parenthesis) {
        _matched=true;
        String _output = output;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("( ");
        String _resolve = this.resolve(((Parenthesis)expression).getExp());
        _builder.append(_resolve);
        _builder.append(" )");
        output = (_output + _builder);
      }
    }
    if (!_matched) {
      if (expression instanceof VariableUse) {
        _matched=true;
        String _output = output;
        String _name = ((VariableUse)expression).getRef().getName();
        output = (_output + _name);
      }
    }
    if (!_matched) {
      if (expression instanceof Plus) {
        _matched=true;
        String _output = output;
        StringConcatenation _builder = new StringConcatenation();
        String _resolve = this.resolve(((Plus)expression).getLeft());
        _builder.append(_resolve);
        _builder.append(" + ");
        String _resolve_1 = this.resolve(((Plus)expression).getRight());
        _builder.append(_resolve_1);
        output = (_output + _builder);
      }
    }
    if (!_matched) {
      if (expression instanceof Minus) {
        _matched=true;
        String _output = output;
        StringConcatenation _builder = new StringConcatenation();
        String _resolve = this.resolve(((Minus)expression).getLeft());
        _builder.append(_resolve);
        _builder.append(" - ");
        String _resolve_1 = this.resolve(((Minus)expression).getRight());
        _builder.append(_resolve_1);
        output = (_output + _builder);
      }
    }
    if (!_matched) {
      if (expression instanceof Div) {
        _matched=true;
        String _output = output;
        StringConcatenation _builder = new StringConcatenation();
        String _resolve = this.resolve(((Div)expression).getLeft());
        _builder.append(_resolve);
        _builder.append(" / ");
        String _resolve_1 = this.resolve(((Div)expression).getRight());
        _builder.append(_resolve_1);
        output = (_output + _builder);
      }
    }
    if (!_matched) {
      if (expression instanceof Mult) {
        _matched=true;
        String _output = output;
        StringConcatenation _builder = new StringConcatenation();
        String _resolve = this.resolve(((Mult)expression).getLeft());
        _builder.append(_resolve);
        _builder.append(" * ");
        String _resolve_1 = this.resolve(((Mult)expression).getRight());
        _builder.append(_resolve_1);
        output = (_output + _builder);
      }
    }
    return output;
  }
}
