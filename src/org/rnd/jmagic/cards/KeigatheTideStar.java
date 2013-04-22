package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Keiga, the Tide Star")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.DRAGON})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class KeigatheTideStar extends Card
{
	public static final class DeathControl extends EventTriggeredAbility
	{
		public DeathControl(GameState state)
		{
			super(state, "When Keiga, the Tide Star dies, gain control of target creature.");
			this.addPattern(whenThisDies());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			this.addEffect(createFloatingEffect(Empty.instance(), "Gain control of target creature.", part));
		}
	}

	public KeigatheTideStar(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Keiga, the Tide Star is put into a graveyard from the
		// battlefield, gain control of target creature.
		this.addAbility(new DeathControl(state));
	}
}
