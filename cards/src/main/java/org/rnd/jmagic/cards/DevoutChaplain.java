package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Devout Chaplain")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class DevoutChaplain extends Card
{
	public static final class DevoutChaplainAbility0 extends ActivatedAbility
	{
		public DevoutChaplainAbility0(GameState state)
		{
			super(state, "(T), Tap two untapped Humans you control: Exile target artifact or enchantment.");
			this.costsTap = true;

			// Tap two untapped Humans you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap two untapped Humans you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.HUMAN)));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addCost(cost);

			SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));
			this.addEffect(exile(target, "Exile target artifact or enchantment."));
		}
	}

	public DevoutChaplain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T), Tap two untapped Humans you control: Exile target artifact or
		// enchantment.
		this.addAbility(new DevoutChaplainAbility0(state));
	}
}
