package it.unifi.ing.stlab.reflection.visitor.fact;

import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class FactReaderVisitor
  implements FactVisitor
{
  private StringBuffer result;
  private int indent;

  public FactReaderVisitor()
  {
    this.indent = 0;
    this.result = new StringBuffer();
  }

  public String getResult()
  {
    return this.result.toString();
  }

  public void visitTextual(TextualFact fact)
  {
    this.result.append("\"");
    this.result.append(fact.getText());
    this.result.append("\"");
  }

  public void visitQuantitative(QuantitativeFact fact)
  {
    this.result.append("\"");
    this.result.append(fact.getQuantity().getValue() != null ? fact.getQuantity().getValue().toString() : "");
    this.result.append(" ");
    this.result.append(fact.getQuantity().getUnit().getSimbol());
    this.result.append("\"");
  }

  public void visitQualitative(QualitativeFact fact)
  {
    this.result.append("\"");
    this.result.append(fact.getPhenomenon() != null ? fact.getPhenomenon().getName() : "");
    this.result.append("\"");
  }

  public void visitTemporal(TemporalFact fact)
  {
    this.result.append("\"");
    this.result.append(fact.getDate() != null ? fact.getDate().toString() : "");
    this.result.append("\"");
  }

  public void visitComposite(CompositeFact fact)
  {
    this.result.append("{\n");
    this.indent += 1;
    for (FactLink link : fact.listActiveLinks())
    {
      tab();
      this.result.append("\"");
      this.result.append(link.getType().getName());
      this.result.append("\"");
      this.result.append(" : ");
      link.getTarget().accept(this);
      this.result.append("\n");
    }
    this.indent -= 1;
    tab();
    this.result.append("}\n");
  }

  private void tab()
  {
    for (int i = 0; i < this.indent; i++) {
      this.result.append("  ");
    }
  }
}
