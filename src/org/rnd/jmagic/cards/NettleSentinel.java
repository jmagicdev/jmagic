package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Nettle Sentinel")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class NettleSentinel extends Card
{
	public static final class NettleSentinelAbility1 extends EventTriggeredAbility
	{
		public NettleSentinelAbility1(GameState state)
		{
			super(state, "Whenever you cast a green spell, you may untap Nettle Sentinel.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.GREEN), Spells.instance()));
			this.addPattern(pattern);

			EventFactory untap = untap(ABILITY_SOURCE_OF_THIS, "Untap Nettle Sentinel");
			this.addEffect(youMay(untap, "You may untap Nettle Sentinel."));
		}
	}

	public NettleSentinel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Nettle Sentinel doesn't untap during your untap step.
		this.addAbility(new org.rnd.jmagic.abilities.DoesntUntapDuringYourUntapStep(state, this.getName()));

		// Whenever you cast a green spell, you may untap Nettle Sentinel.
		this.addAbility(new NettleSentinelAbility1(state));
	}
}
