package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Teferi, Mage of Zhalfir")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2UUU")
@Printings({@Printings.Printed(ex = FromTheVaultLegends.class, r = Rarity.MYTHIC), @Printings.Printed(ex = TimeSpiral.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TeferiMageofZhalfir extends Card
{
	public static final class Permission extends StaticAbility
	{
		public Permission(GameState state)
		{
			super(state, "Creature cards you own that aren't on the battlefield have flash.");

			SetGenerator creatureCardsYouOwn = Intersect.instance(HasType.instance(Type.CREATURE), OwnedBy.instance(You.instance()));
			SetGenerator thatArentOnTheBattlefield = RelativeComplement.instance(creatureCardsYouOwn, InZone.instance(Battlefield.instance()));

			this.addEffectPart(addAbilityToObject(thatArentOnTheBattlefield, org.rnd.jmagic.abilities.keywords.Flash.class));
		}
	}

	public static final class Restriction extends StaticAbility
	{
		/**
		 * This basically just says "the stack is empty and its a main phase".
		 */
		public static final class SorcerySpeedIsh extends SetGenerator
		{
			private static SorcerySpeedIsh _instance = new SorcerySpeedIsh();

			public static SorcerySpeedIsh instance()
			{
				return _instance;
			}

			private SorcerySpeedIsh()
			{
				// Singleton generator
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				if(state.currentTurn() == null)
					return Empty.set;

				if(state.currentPhase().type != Phase.PhaseType.PRECOMBAT_MAIN && state.currentPhase().type != Phase.PhaseType.POSTCOMBAT_MAIN)
					return Empty.set;

				if(!state.stack().objects.isEmpty())
					return Empty.set;

				return NonEmpty.set;
			}
		}

		public Restriction(GameState state)
		{
			super(state, "Each opponent can cast spells only any time he or she could cast a sorcery.");

			// All opponents (minus the current turns controller, if that player
			// can play at sorcery speed)
			SetGenerator currentTurnsOwner = OwnerOf.instance(CurrentTurn.instance());
			SetGenerator affected = RelativeComplement.instance(OpponentsOf.instance(You.instance()), IfThenElse.instance(SorcerySpeedIsh.instance(), currentTurnsOwner, Empty.instance()));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			pattern.put(EventType.Parameter.PLAYER, affected);
			pattern.put(EventType.Parameter.OBJECT, SetPattern.CASTABLE);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(part);
		}
	}

	public TeferiMageofZhalfir(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Creature cards you own that aren't on the battlefield have flash.
		this.addAbility(new Permission(state));

		// Each opponent can cast spells only any time he or she could cast a
		// sorcery.
		this.addAbility(new Restriction(state));
	}
}
