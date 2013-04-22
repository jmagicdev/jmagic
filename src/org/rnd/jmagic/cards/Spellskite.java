package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spellskite")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Spellskite extends Card
{
	public static final class SpellskiteAbility0 extends ActivatedAbility
	{
		public SpellskiteAbility0(GameState state)
		{
			super(state, "(u/p): Change a target of target spell or ability to Spellskite.");
			this.setManaCost(new ManaPool("(u/p)"));

			SetGenerator target = targetedBy(this.addTarget(InZone.instance(Stack.instance()), "target spell or ability"));

			EventFactory factory = new EventFactory(EventType.CHANGE_SINGLE_TARGET_TO, "Change a target of target spell or ability to Spellskite.");
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			factory.parameters.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public Spellskite(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// (u/p): Change a target of target spell or ability to Spellskite.
		// ((u/p) can be paid with either (U) or 2 life.)
		this.addAbility(new SpellskiteAbility0(state));
	}
}
