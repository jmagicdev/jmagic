package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Niv-Mizzet, the Firemind")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.DRAGON})
@ManaCost("2UURR")
@Printings({@Printings.Printed(ex = Expansion.DRAGONS, r = Rarity.RARE), @Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class NivMizzettheFiremind extends Card
{
	public static final class NivMizzettheFiremindAbility1 extends EventTriggeredAbility
	{
		public NivMizzettheFiremindAbility1(GameState state)
		{
			super(state, "Whenever you draw a card, Niv-Mizzet, the Firemind deals 1 damage to target creature or player.");
			SimpleEventPattern draw = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			draw.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(draw);

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(1, target, "Niv-Mizzet, the Firemind deals 1 damage to target creature or player."));
		}
	}

	public static final class NivMizzettheFiremindAbility2 extends ActivatedAbility
	{
		public NivMizzettheFiremindAbility2(GameState state)
		{
			super(state, "(T): Draw a card.");
			this.costsTap = true;
			this.addEffect(drawACard());
		}
	}

	public NivMizzettheFiremind(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you draw a card, Niv-Mizzet, the Firemind deals 1 damage to
		// target creature or player.
		this.addAbility(new NivMizzettheFiremindAbility1(state));

		// (T): Draw a card.
		this.addAbility(new NivMizzettheFiremindAbility2(state));
	}
}
