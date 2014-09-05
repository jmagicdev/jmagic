package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Soldier of the Pantheon")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER,SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.THEROS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SoldierofthePantheon extends Card
{
	public static final class ProtectionFromMulticolored extends org.rnd.jmagic.abilities.keywords.Protection
	{
		public ProtectionFromMulticolored(GameState state)
		{
			super(state, Multicolored.instance(), "multicolored");
		}
	}

	public static final class SoldierofthePantheonAbility1 extends EventTriggeredAbility
	{
		public SoldierofthePantheonAbility1(GameState state)
		{
			super(state, "Whenever an opponent casts a multicolored spell, you gain 1 life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), Multicolored.instance()));
			this.addPattern(pattern);

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public SoldierofthePantheon(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Protection from multicolored
		this.addAbility(new ProtectionFromMulticolored(state));

		// Whenever an opponent casts a multicolored spell, you gain 1 life.
		this.addAbility(new SoldierofthePantheonAbility1(state));
	}
}
