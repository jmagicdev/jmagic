/**
 * 
 */
package org.rnd.jmagic.gui;

import org.rnd.jmagic.engine.*;

class StepLabel extends javax.swing.JTextPane
{
	private static final long serialVersionUID = 1L;

	private int currentIndex;
	private Step.StepType highlighted;
	private transient javax.swing.text.Highlighter highlighter;

	private transient javax.swing.text.Highlighter.HighlightPainter highlightPainter;
	java.util.Map<Step.StepType, org.rnd.util.NumberRange> stepTextRanges;
	private StringBuilder text;

	public StepLabel()
	{
		super();

		this.stepTextRanges = new java.util.HashMap<Step.StepType, org.rnd.util.NumberRange>();
		this.currentIndex = 0;
		this.text = new StringBuilder();
		this.addStep(Step.StepType.UNTAP);
		this.addStep(Step.StepType.UPKEEP);
		this.addStep(Step.StepType.DRAW);
		this.addStep(Step.StepType.PRECOMBAT_MAIN);
		this.addStep(Step.StepType.BEGINNING_OF_COMBAT);
		this.addStep(Step.StepType.DECLARE_ATTACKERS);
		this.addStep(Step.StepType.DECLARE_BLOCKERS);
		this.addStep(Step.StepType.COMBAT_DAMAGE);
		this.addStep(Step.StepType.END_OF_COMBAT);
		this.addStep(Step.StepType.POSTCOMBAT_MAIN);
		this.addStep(Step.StepType.END);
		this.addStep(Step.StepType.CLEANUP);

		this.setText(this.text.toString());

		this.setBorder(null);
		this.setOpaque(false);
		this.setEditable(false);

		this.highlighted = null;
		this.highlighter = new javax.swing.text.DefaultHighlighter();
		this.setHighlighter(this.highlighter);
		this.highlightPainter = new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(java.awt.Color.YELLOW);

		this.setMinimumSize(this.getPreferredSize());
	}

	private void addStep(Step.StepType s)
	{
		String text = s.toString();
		int length = text.length();
		this.stepTextRanges.put(s, new org.rnd.util.NumberRange(this.currentIndex, this.currentIndex + length));

		this.text.append(text + (s == Step.StepType.CLEANUP ? "" : "\n"));
		this.currentIndex += length + 1;
	}

	public void highlight(Step.StepType s)
	{
		if(this.highlighted == s)
			return;
		this.highlighted = s;

		this.highlighter.removeAllHighlights();
		org.rnd.util.NumberRange range = this.stepTextRanges.get(s);

		try
		{
			if(range != null)
				this.highlighter.addHighlight(range.getLower(), range.getUpper(), this.highlightPainter);
		}
		catch(javax.swing.text.BadLocationException e)
		{
			throw new RuntimeException("[ this should never happen ] Error highlighting " + s);
		}
	}

	public String getHighlighted()
	{
		return this.highlighted.toString();
	}
}