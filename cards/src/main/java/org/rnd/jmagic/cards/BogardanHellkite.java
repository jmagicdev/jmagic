package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bogardan Hellkite")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("6RR")
@ColorIdentity({Color.RED})
public final class BogardanHellkite extends Card
{
	public static final class FlashDamage extends EventTriggeredAbility
	{
		public FlashDamage(GameState state)
		{
			super(state, "When Bogardan Hellkite enters the battlefield, it deals 5 damage divided as you choose among any number of target creatures and/or players.");
			this.addPattern(whenThisEntersTheBattlefield());

			// it deals 5 damage divided as you choose among any number of
			// target creatures and/or players.
			Target target = this.addTarget(CREATURES_AND_PLAYERS, "up to five target creatures and/or players");
			target.setNumber(1, 5);
			this.setDivision(Union.instance(numberGenerator(5), Identity.instance("damage")));

			EventType.ParameterMap damageParameters = new EventType.ParameterMap();
			damageParameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
			this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Bogardan Hellkite deals 5 damage divided as you choose among any number of target creatures and/or players."));
		}
	}

	public BogardanHellkite(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new FlashDamage(state));
	}
}
