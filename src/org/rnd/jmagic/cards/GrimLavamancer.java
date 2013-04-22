package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grim Lavamancer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("R  ")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TORMENT, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class GrimLavamancer extends Card
{
	public static final class Shocker extends ActivatedAbility
	{
		public Shocker(GameState state)
		{
			super(state, "(R), (T), Exile two cards from your graveyard: Grim Lavamancer deals 2 damage to target creature or player.");
			this.setManaCost(new ManaPool("R"));
			this.costsTap = true;

			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "Exile two cards from your graveyard");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			exile.parameters.put(EventType.Parameter.OBJECT, InZone.instance(GraveyardOf.instance(You.instance())));
			exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addCost(exile);

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Grim Lavamancer deals 2 damage to target creature or player."));
		}
	}

	public GrimLavamancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (R), (T), Exile two cards from your graveyard: Grim Lavamancer deals
		// 2 damage to target creature or player.
		this.addAbility(new Shocker(state));
	}
}
