package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ignite Disorder")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class IgniteDisorder extends Card
{
	public IgniteDisorder(GameState state)
	{
		super(state);

		// Ignite Disorder deals 3 damage divided as you choose among any number
		// of target white and/or blue creatures.
		SetGenerator whiteAndBlue = HasColor.instance(Color.WHITE, Color.BLUE);
		SetGenerator whiteAndBlueCreatures = Intersect.instance(whiteAndBlue, CreaturePermanents.instance());
		Target target = this.addTarget(whiteAndBlueCreatures, "target any number of target white and/or blue creatures");
		target.setNumber(1, 3);

		this.setDivision(Union.instance(numberGenerator(3), Identity.instance("damage")));
		EventType.ParameterMap damageParameters = new EventType.ParameterMap();
		damageParameters.put(EventType.Parameter.SOURCE, This.instance());
		damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Ignite Disorder deals 3 damage divided as you choose among any number of target white and/or blue creatures."));
	}
}
