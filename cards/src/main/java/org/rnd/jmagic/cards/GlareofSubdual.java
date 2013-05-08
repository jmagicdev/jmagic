package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glare of Subdual")
@Types({Type.ENCHANTMENT})
@ManaCost("2GW")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GlareofSubdual extends Card
{
	public static final class GlareofSubdualAbility0 extends ActivatedAbility
	{
		public GlareofSubdualAbility0(GameState state)
		{
			super(state, "Tap an untapped creature you control: Tap target artifact or creature.");

			SetGenerator yourUntappedCreatures = Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL);

			EventFactory tapACreature = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped creature you control");
			tapACreature.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapACreature.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapACreature.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			tapACreature.parameters.put(EventType.Parameter.CHOICE, yourUntappedCreatures);
			this.addCost(tapACreature);

			Target t = this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact or creature");
			this.addEffect(tap(targetedBy(t), "Tap target artifact or creature."));
		}
	}

	public GlareofSubdual(GameState state)
	{
		super(state);

		// Tap an untapped creature you control: Tap target artifact or
		// creature.
		this.addAbility(new GlareofSubdualAbility0(state));
	}
}
