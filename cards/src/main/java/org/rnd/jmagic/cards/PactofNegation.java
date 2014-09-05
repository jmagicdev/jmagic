package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Pact of Negation")
@ManaCost("0")
@Types({Type.INSTANT})
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class PactofNegation extends org.rnd.jmagic.cardTemplates.Pact
{
	public PactofNegation(GameState state)
	{
		super(state);
	}

	@Override
	public void addEffects()
	{
		Target target = this.addTarget(org.rnd.jmagic.engine.generators.Spells.instance(), "target spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target spell."));
	}

	@Override
	public Color getColor()
	{
		return Color.BLUE;
	}

	@Override
	public String getUpkeepCost()
	{
		return "(3)(U)(U)";
	}
}
