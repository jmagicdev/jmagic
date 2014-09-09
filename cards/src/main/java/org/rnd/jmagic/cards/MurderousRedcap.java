package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Murderous Redcap")
@Types({Type.CREATURE})
@SubTypes({SubType.ASSASSIN, SubType.GOBLIN})
@ManaCost("2(B/R)(B/R)")
@ColorIdentity({Color.BLACK, Color.RED})
public final class MurderousRedcap extends Card
{
	public static final class CappableRedrum extends EventTriggeredAbility
	{
		public CappableRedrum(GameState state)
		{
			super(state, "When Murderous Redcap enters the battlefield, it deals damage equal to its power to target creature or player.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(PowerOf.instance(ABILITY_SOURCE_OF_THIS), targetedBy(target), "It deals damage equal to its power to target creature or playter"));
		}
	}

	public MurderousRedcap(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new CappableRedrum(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Persist(state));
	}
}
