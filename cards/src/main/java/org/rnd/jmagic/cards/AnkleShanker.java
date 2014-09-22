package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.ContinuousEffect.Part;

@Name("Ankle Shanker")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.BERSERKER})
@ManaCost("2RWB")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.RED})
public final class AnkleShanker extends Card
{
	public static final class AnkleShankerAbility1 extends EventTriggeredAbility
	{
		public AnkleShankerAbility1(GameState state)
		{
			super(state, "Whenever Ankle Shanker attacks, creatures you control gain first strike and deathtouch until end of turn.");
			this.addPattern(whenThisAttacks());
			Part abilities = addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			this.addEffect(createFloatingEffect("Creatures you control gain first strike and deathtouch until end of turn.", abilities));
		}
	}

	public AnkleShanker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever Ankle Shanker attacks, creatures you control gain first
		// strike and deathtouch until end of turn.
		this.addAbility(new AnkleShankerAbility1(state));
	}
}
