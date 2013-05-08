package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sower of Temptation")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.FAERIE})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SowerofTemptation extends Card
{
	public static final class Tempt extends EventTriggeredAbility
	{
		public Tempt(GameState state)
		{
			super(state, "When Sower of Temptation enters the battlefield, gain control of target creature for as long as Sower of Temptation remains on the battlefield.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			SetGenerator expires = Intersect.instance(ABILITY_SOURCE_OF_THIS, Permanents.instance());

			this.addEffect(createFloatingEffect(expires, "Gain control of target creature for as long as Sower of Temptation remains on the battlefield.", part));
		}
	}

	public SowerofTemptation(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new Tempt(state));
	}
}
