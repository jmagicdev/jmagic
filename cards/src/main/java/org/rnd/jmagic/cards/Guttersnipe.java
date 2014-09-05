package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Guttersnipe")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Guttersnipe extends Card
{
	public static final class GuttersnipeAbility0 extends EventTriggeredAbility
	{
		public GuttersnipeAbility0(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, Guttersnipe deals 2 damage to each opponent.");

			SetGenerator instantOrSorcery = HasType.instance(Type.INSTANT, Type.SORCERY);
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(instantOrSorcery, Spells.instance()));
			this.addPattern(pattern);

			this.addEffect(permanentDealDamage(2, OpponentsOf.instance(You.instance()), "Guttersnipe deals 2 damage to each opponent."));
		}
	}

	public Guttersnipe(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever you cast an instant or sorcery spell, Guttersnipe deals 2
		// damage to each opponent.
		this.addAbility(new GuttersnipeAbility0(state));
	}
}
