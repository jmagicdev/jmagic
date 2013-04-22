package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Timely Reinforcements")
@Types({Type.SORCERY})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class TimelyReinforcements extends Card
{
	public TimelyReinforcements(GameState state)
	{
		super(state);

		// If you have less life than an opponent, you gain 6 life.
		SetGenerator maxLife = Maximum.instance(LifeTotalOf.instance(OpponentsOf.instance(You.instance())));
		SetGenerator lessLife = Intersect.instance(LifeTotalOf.instance(You.instance()), Between.instance(null, Subtract.instance(maxLife, numberGenerator(1))));

		EventFactory gainLife = gainLife(You.instance(), 6, "You gain 6 life");
		this.addEffect(ifThen(lessLife, gainLife, "If you have less life than an opponent, you gain 6 life."));

		// If you control fewer creatures than an opponent, put three 1/1 white
		// Soldier creature tokens onto the battlefield.
		SetGenerator theirCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
		SetGenerator maxCreatures = Count.instance(LargestSet.instance(SplitOnController.instance(theirCreatures)));
		SetGenerator fewerCreatures = Intersect.instance(Count.instance(CREATURES_YOU_CONTROL), Between.instance(null, Subtract.instance(maxCreatures, numberGenerator(1))));

		CreateTokensFactory f = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Soldier creature tokens onto the battlefield");
		f.setColors(Color.WHITE);
		f.setSubTypes(SubType.SOLDIER);
		this.addEffect(ifThen(fewerCreatures, f.getEventFactory(), "If you control fewer creatures than an opponent, put three 1/1 white Soldier creature tokens onto the battlefield."));
	}
}
