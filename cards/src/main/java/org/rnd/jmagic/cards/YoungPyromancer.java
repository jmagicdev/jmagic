package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.expansions.*;

@Name("Young Pyromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Magic2014CoreSet.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class YoungPyromancer extends Card
{
	public static final class YoungPyromancerAbility0 extends EventTriggeredAbility
	{
		public YoungPyromancerAbility0(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, put a 1/1 red Elemental creature token onto the battlefield.");

			SetGenerator instantOrSorcery = HasType.instance(Type.INSTANT, Type.SORCERY);
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(instantOrSorcery, Spells.instance()));
			this.addPattern(pattern);

			CreateTokensFactory elemental = new CreateTokensFactory(1, 1, 1, "Put a 1/1 red Elemental creature token onto the battlefield.");
			elemental.setColors(Color.RED);
			elemental.setSubTypes(SubType.ELEMENTAL);
			this.addEffect(elemental.getEventFactory());
		}
	}

	public YoungPyromancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever you cast an instant or sorcery spell, put a 1/1 red
		// Elemental creature token onto the battlefield.
		this.addAbility(new YoungPyromancerAbility0(state));
	}
}
