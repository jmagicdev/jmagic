package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Slaughter Pact")
@ManaCost("0")
@Types({Type.INSTANT})
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class SlaughterPact extends org.rnd.jmagic.cardTemplates.Pact
{
	public SlaughterPact(GameState state)
	{
		super(state);
	}

	@Override
	public void addEffects()
	{
		Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK)), "target nonblack creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target nonblack creature."));
	}

	@Override
	public Color getColor()
	{
		return Color.BLACK;
	}

	@Override
	public String getUpkeepCost()
	{
		return "(2)(B)";
	}
}
