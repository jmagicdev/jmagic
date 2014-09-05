package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rust Scarab")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class RustScarab extends Card
{
	public static final class RustScarabAbility0 extends EventTriggeredAbility
	{
		public RustScarabAbility0(GameState state)
		{
			super(state, "Whenever Rust Scarab becomes blocked, you may destroy target artifact or enchantment defending player controls.");
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			pattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			SetGenerator artifactOrEnchantment = HasType.instance(Type.ARTIFACT, Type.ENCHANTMENT);
			SetGenerator controlledByDefendingPlayer = ControlledBy.instance(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS));
			SetGenerator restriction = Intersect.instance(artifactOrEnchantment, controlledByDefendingPlayer);
			SetGenerator target = targetedBy(this.addTarget(restriction, "target artifact or enchantment defending player controls"));
			EventFactory destroy = destroy(target, "destroy target artifact or enchantment defending player controls");
			this.addEffect(youMay(destroy, "You may destroy target artifact or enchantment defending player controls."));
		}
	}

	public RustScarab(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Whenever Rust Scarab becomes blocked, you may destroy target artifact
		// or enchantment defending player controls.
		this.addAbility(new RustScarabAbility0(state));
	}
}
