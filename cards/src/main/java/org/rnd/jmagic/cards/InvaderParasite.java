package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Invader Parasite")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class InvaderParasite extends Card
{
	public static final class InvaderParasiteAbility0 extends EventTriggeredAbility
	{
		public InvaderParasiteAbility0(GameState state)
		{
			super(state, "When Invader Parasite enters the battlefield, exile target land.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
			EventFactory effect = exile(target, "Exile target land.");
			effect.setLink(this);
			this.addEffect(effect);

			this.getLinkManager().addLinkClass(InvaderParasiteAbility1.class);
		}
	}

	public static final class InvaderParasiteAbility1 extends EventTriggeredAbility
	{
		public InvaderParasiteAbility1(GameState state)
		{
			super(state, "Whenever a land with the same name as the exiled card enters the battlefield under an opponent's control, Invader Parasite deals 2 damage to that player.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), Intersect.instance(HasType.instance(Type.LAND), HasName.instance(NameOf.instance(ChosenFor.instance(LinkedTo.instance(This.instance()))))), false));
			this.addEffect(permanentDealDamage(2, ControllerOf.instance(NewObjectOf.instance(TriggerZoneChange.instance(This.instance()))), "Invader Parasite deals 2 damage to that player."));

			this.getLinkManager().addLinkClass(InvaderParasiteAbility0.class);
		}
	}

	public InvaderParasite(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Imprint \u2014 When Invader Parasite enters the battlefield, exile
		// target land.
		this.addAbility(new InvaderParasiteAbility0(state));

		// Whenever a land with the same name as the exiled card enters the
		// battlefield under an opponent's control, Invader Parasite deals 2
		// damage to that player.
		this.addAbility(new InvaderParasiteAbility1(state));
	}
}
