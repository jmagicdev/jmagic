package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Call to the Grave")
@Types({Type.ENCHANTMENT})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class CalltotheGrave extends Card
{
	public static final class SacNonZombie extends EventTriggeredAbility
	{
		public SacNonZombie(GameState state)
		{
			super(state, "At the beginning of each player's upkeep, that player sacrifices a non-Zombie creature.");
			this.addPattern(atTheBeginningOfEachPlayersUpkeep());

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));
			SetGenerator nonZombies = RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.ZOMBIE));
			this.addEffect(sacrifice(thatPlayer, 1, nonZombies, "That player sacrifices a non-Zombie creature."));
		}
	}

	public static final class SacThis extends EventTriggeredAbility
	{
		public SacThis(GameState state)
		{
			super(state, "At the beginning of the end step, if no creatures are on the battlefield, sacrifice Call to the Grave.");
			this.addPattern(atTheBeginningOfTheEndStep());
			this.interveningIf = Not.instance(CreaturePermanents.instance());

			this.addEffect(sacrificeThis("Call to the Grave"));
		}
	}

	public CalltotheGrave(GameState state)
	{
		super(state);

		// At the beginning of each player's upkeep, that player sacrifices a
		// non-Zombie creature.
		this.addAbility(new SacNonZombie(state));

		// At the beginning of the end step, if no creatures are on the
		// battlefield, sacrifice Call to the Grave.
		this.addAbility(new SacThis(state));
	}
}
